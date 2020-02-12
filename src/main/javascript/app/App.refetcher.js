import * as _ from 'lodash';

const refetcherRegistry = [];

const add = (name, refetch, variables) => {
    if (_.isNil(name) || _.isNil(refetch)) {
        return;
    }

    refetcherRegistry.push({name: name, refetch: refetch, variables: variables});
};

const runByName = name => {
    if (_.isNil(name)) {
        return;
    }

    const registry = refetcherRegistry.find(registry => registry.name === name);
    if (!_.isNil(registry.variables)) {
        registry.refetch(registry.variables);
    } else {
        registry.refetch();
    }
};

const runAll = () => {
    refetcherRegistry.forEach(registry => {
        if (!_.isNil(registry.variables)) {
            registry.refetch(registry.variables);
        } else {
            registry.refetch();
        }
    });
};

const Refetcher = {
    add: add,
    runAll: runAll,
    run: runByName
};

export default Refetcher;
