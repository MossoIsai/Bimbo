package com.mosso.bimbo.core.domain;

import kotlin.Unit;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings("unchecked")
public abstract class BaseUseCase<Params, Output> {
    public abstract Flow<Output> execute(Params params);

    public Flow<Output> execute() {
        return execute((Params) Unit.INSTANCE);
    }
}
