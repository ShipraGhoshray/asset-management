package com.selflearning.service.factory;

import com.selflearning.enums.AssetTypeEnum;
import com.selflearning.exception.ResourceNotFoundException;
import com.selflearning.service.engine.BaseNettingEngine;
import com.selflearning.service.engine.RepoNettingEngine;
import com.selflearning.service.engine.TbaNettingEngine;
import org.springframework.stereotype.Component;

@Component
public class NettingEngineFactory {
    //private static final Map<String, Supplier<? extends BaseNettingEngine>> engineMap = new HashMap<>();
    private final RepoNettingEngine repoNettingEngine;
    private final TbaNettingEngine tbaNettingEngine;

    public NettingEngineFactory(RepoNettingEngine repoNettingEngine,
                                TbaNettingEngine tbaNettingEngine) {
        this.repoNettingEngine = repoNettingEngine;
        this.tbaNettingEngine = tbaNettingEngine;
    }

    public BaseNettingEngine getNettingInstance(String typeCode) {
        try {
            AssetTypeEnum type = AssetTypeEnum.fromCode(typeCode);
            return switch (type) {
                case REPO, REVREPO ->  repoNettingEngine; //new RepoNettingEngine(pricingClient);
                case TBA -> tbaNettingEngine;
                default -> throw new ResourceNotFoundException(typeCode);
            };
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(typeCode);
        }
    }
}