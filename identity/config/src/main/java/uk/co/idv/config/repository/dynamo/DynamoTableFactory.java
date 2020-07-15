package uk.co.idv.config.repository.dynamo;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class DynamoTableFactory {

    private final String endpointUrl;

    @Builder.Default
    private final String environment = "idv-local";

    @Builder.Default
    private final Regions region = Regions.EU_WEST_1;

    @Builder.Default
    private final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    public DynamoTables build() {
        return new DynamoTables(environment, buildClient());
    }

    private AmazonDynamoDB buildClient() {
        return build(getEndpointConfiguration());
    }

    private EndpointConfiguration getEndpointConfiguration() {
        return new EndpointConfiguration(endpointUrl, region.getName());
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
