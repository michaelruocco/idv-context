package uk.co.idv.context.adapter.dynamo;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class EnvironmentDynamoTablesFactory {

    private final String endpointUrl;
    private final String environment;
    private final String region;

    @Builder.Default
    private final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    public DynamoTables build() {
        return new EnvironmentDynamoTables(environment, buildClient());
    }

    private AmazonDynamoDB buildClient() {
        return build(getEndpointConfiguration());
    }

    private EndpointConfiguration getEndpointConfiguration() {
        return new EndpointConfiguration(endpointUrl, region);
    }

    private AmazonDynamoDB build(EndpointConfiguration endpointConfiguration) {
        log.info("connecting to dynamodb service endpoint {}", endpointConfiguration.getServiceEndpoint());
        log.info("connecting to dynamodb signing region {}", endpointConfiguration.getSigningRegion());
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }

}
