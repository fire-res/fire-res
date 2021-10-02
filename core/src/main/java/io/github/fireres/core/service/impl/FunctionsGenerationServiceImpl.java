package io.github.fireres.core.service.impl;

import io.github.fireres.core.exception.FunctionGenerationException;
import io.github.fireres.core.exception.ImpossibleGenerationException;
import io.github.fireres.core.model.FunctionsGenerationParams;
import io.github.fireres.core.model.IntegerPointSequence;
import io.github.fireres.core.model.MaintainedFunctionsGenerationParams;
import io.github.fireres.core.model.Point;
import io.github.fireres.core.utils.FunctionUtils;
import io.github.fireres.core.properties.FunctionForm;
import io.github.fireres.core.properties.GenerationProperties;
import io.github.fireres.core.generator.ChildrenInterpolationPointsGenerator;
import io.github.fireres.core.generator.FunctionGenerator;
import io.github.fireres.core.generator.MaintainedFunctionGenerator;
import io.github.fireres.core.generator.SimilarFunctionGenerator;
import io.github.fireres.core.service.FunctionsGenerationService;
import io.github.fireres.core.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FunctionsGenerationServiceImpl implements FunctionsGenerationService {

    private static final Integer BASIS_FUNCTION_GENERATION_ATTEMPTS = 100;
    private static final Integer CHILD_FUNCTIONS_GENERATION_ATTEMPTS = 100;

    private final GenerationProperties generationProperties;
    private final ValidationService validationService;

    @Override
    public Pair<IntegerPointSequence, List<IntegerPointSequence>> meanWithChildFunctions(
            FunctionsGenerationParams generationParameters
    ) {
        if (!validationService.isFunctionGenerationParamsValid(generationParameters)) {
            throw new ImpossibleGenerationException();
        }

        return generateMeanWithChildFunctions(generationParameters);
    }

    @Override
    public List<IntegerPointSequence> maintainedFunctions(MaintainedFunctionsGenerationParams generationParameters) {
        return MaintainedFunctionGenerator.builder()
                .temperature(generationParameters.getTemperature())
                .tStart(generationParameters.getTStart())
                .tEnd(generationParameters.getTEnd())
                .lowerBound(generationParameters.getBounds().getLowerBound())
                .upperBound(generationParameters.getBounds().getUpperBound())
                .functionsCount(generationParameters.getFunctionsCount())
                .build()
                .generate();
    }

    private Pair<IntegerPointSequence, List<IntegerPointSequence>> generateMeanWithChildFunctions(FunctionsGenerationParams params) {
        for (int basisAttempt = 0; basisAttempt < BASIS_FUNCTION_GENERATION_ATTEMPTS; basisAttempt++) {
            val basis = generateBasis(params);
            val childFunctionsForms = mapToChildFunctionsForms(params);
            val childFunctions = new ArrayList<IntegerPointSequence>();

            try {
                for (int i = 0; i < params.getChildFunctionsCount(); i++) {
                    val childFunctionForm = childFunctionsForms.get(i);
                    val childFunction = generateChildFunction(basis, childFunctionForm, params);

                    childFunctions.add(childFunction);
                }

                return Pair.create(FunctionUtils.calculateMeanFunction(childFunctions), childFunctions);
            } catch (ImpossibleGenerationException e) {
                log.error("Can't generate child function, generating new basis, attempt: {}", basisAttempt);
            }
        }

        throw new ImpossibleGenerationException();
    }

    private IntegerPointSequence generateChildFunction(IntegerPointSequence basis,
                                                       FunctionForm<Integer> functionForm,
                                                       FunctionsGenerationParams params) {

        for (int childAttempt = 0; childAttempt < CHILD_FUNCTIONS_GENERATION_ATTEMPTS; childAttempt++) {
            try {
                return SimilarFunctionGenerator.builder()
                        .basis(basis)
                        .originalForm(functionForm)
                        .lowerBound(params.getChildrenBounds().getLowerBound())
                        .upperBound(params.getChildrenBounds().getUpperBound())
                        .t0(generationProperties.getGeneral().getEnvironmentTemperature())
                        .time(generationProperties.getGeneral().getTime())
                        .functionsGenerationStrategy(params.getStrategy())
                        .build()
                        .generate();
            } catch (FunctionGenerationException e) {
                log.error("Failed to generate child function, attempt: {}", childAttempt);
            }
        }

        throw new ImpossibleGenerationException();
    }

    private IntegerPointSequence generateBasis(FunctionsGenerationParams generationParams) {
        for (int attempt = 0; attempt < CHILD_FUNCTIONS_GENERATION_ATTEMPTS; attempt++) {
            try {
                return FunctionGenerator.builder()
                        .functionForm(generationParams.getMeanFunctionForm())
                        .lowerBound(generationParams.getMeanBounds().getLowerBound())
                        .upperBound(generationParams.getMeanBounds().getUpperBound())
                        .t0(generationProperties.getGeneral().getEnvironmentTemperature())
                        .time(generationProperties.getGeneral().getTime())
                        .build()
                        .generate();
            } catch (FunctionGenerationException e) {
                log.error("Failed to generate basis, attempt: {}", attempt);
            }
        }

        throw new ImpossibleGenerationException();
    }

    private List<FunctionForm<Integer>> mapToChildFunctionsForms(FunctionsGenerationParams params) {
        val childForms = initializeChildrenForms(params);

        initializeInterpolationPoints(childForms, params);

        return childForms;
    }

    private void initializeInterpolationPoints(List<FunctionForm<Integer>> childForms,
                                               FunctionsGenerationParams params) {

        val envTemp = generationProperties.getGeneral().getEnvironmentTemperature();

        for (Point<?> meanPoint : params.getMeanFunctionForm().getInterpolationPoints()) {
            val delta = params.getStrategy().resolveDelta(envTemp, meanPoint.getIntValue(),
                    params.getMeanFunctionForm().getChildFunctionsDeltaCoefficient());

            val childrenPoints = ChildrenInterpolationPointsGenerator.builder()
                    .childForms(childForms)
                    .childrenCount(params.getChildFunctionsCount())
                    .time(meanPoint.getTime())
                    .meanValue(meanPoint.getIntValue())
                    .lowerBound(params.getChildrenBounds().getLowerBound().getPoint(meanPoint.getTime()).getValue())
                    .upperBound(params.getChildrenBounds().getUpperBound().getPoint(meanPoint.getTime()).getValue())
                    .maxDelta(delta / 2)
                    .build()
                    .generate();

            for (int i = 0; i < childrenPoints.getValue().size(); i++) {
                childForms.get(i).getInterpolationPoints().add(childrenPoints.getValue().get(i));
            }
        }
    }


    private List<FunctionForm<Integer>> initializeChildrenForms(FunctionsGenerationParams params) {
        return IntStream.range(0, params.getChildFunctionsCount())
                .mapToObj(i -> {
                    val childForm = new FunctionForm<Integer>();

                    childForm.setDispersionCoefficient(params.getMeanFunctionForm().getDispersionCoefficient());
                    childForm.setLinearityCoefficient(params.getMeanFunctionForm().getLinearityCoefficient());
                    childForm.setChildFunctionsDeltaCoefficient(params.getMeanFunctionForm().getChildFunctionsDeltaCoefficient());

                    return childForm;
                })
                .collect(Collectors.toList());
    }

}
