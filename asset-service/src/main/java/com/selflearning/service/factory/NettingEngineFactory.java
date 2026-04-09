package com.selflearning.service.factory;

import com.selflearning.enums.AssetTypeEnum;
import com.selflearning.exception.UnknownAssetTypeException;
import com.selflearning.service.engine.BaseNettingEngine;
import com.selflearning.service.engine.RepoNettingEngine;
import com.selflearning.service.engine.TbaNettingEngine;
import com.selflearning.service.impl.PricingClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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
                default -> throw new UnknownAssetTypeException(typeCode);
            };
        } catch (IllegalArgumentException e) {
            throw new UnknownAssetTypeException(typeCode);
        }
    }
}