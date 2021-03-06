package io.smallrye.graphql.client.typesafe.api;

import java.net.URI;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Use this builder, when you are not in a CDI context, i.e. when working with Java SE.
 */
public interface TypesafeGraphQLClientBuilder {

    static TypesafeGraphQLClientBuilder newBuilder() {
        ServiceLoader<TypesafeGraphQLClientBuilder> loader = ServiceLoader.load(TypesafeGraphQLClientBuilder.class);
        Iterator<TypesafeGraphQLClientBuilder> iterator = loader.iterator();
        if (!iterator.hasNext())
            throw new ServiceConfigurationError("no " + TypesafeGraphQLClientBuilder.class.getName() + " in classpath");
        TypesafeGraphQLClientBuilder graphQlClientBuilder = iterator.next();
        if (iterator.hasNext())
            throw new ServiceConfigurationError(
                    "more than one " + TypesafeGraphQLClientBuilder.class.getName() + " in classpath");
        return graphQlClientBuilder;
    }

    /**
     * The base key used to read configuration values. Defaults to the fully qualified name of the API interface.
     */
    TypesafeGraphQLClientBuilder configKey(String configKey);

    /**
     * The URL where the GraphQL service is listening
     */
    default TypesafeGraphQLClientBuilder endpoint(String endpoint) {
        return endpoint(URI.create(endpoint));
    }

    /**
     * The URL where the GraphQL service is listening
     */
    TypesafeGraphQLClientBuilder endpoint(URI endpoint);

    <T> T build(Class<T> apiClass);

}
