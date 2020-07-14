package uk.co.idv.config.repository.dynamo;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Builder
public class DynamoTableFactory {

    private final String endpointUrl;

    @Builder.Default
    private final String environment = "idv-local";

    @Builder.Default
    private final String region = Regions.EU_WEST_1.getName();

    @Builder.Default
    private final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    public DynamoTables build() {
        return new DynamoTables(environment, buildClient());
    }

    private AmazonDynamoDB buildClient() {
        return getEndpointConfiguration()
                .map(this::build)
                .orElseGet(() -> build(region));
    }

    private Optional<EndpointConfiguration> getEndpointConfiguration() {
        return Optional.ofNullable(endpointUrl)
                .map(endpoint -> new EndpointConfiguration(endpoint, region));
    }

    private AmazonDynamoDB build(EndpointConfiguration endpointConfiguration) {
        log.info("connecting to dynamodb service endpoint {}", endpointConfiguration.getServiceEndpoint());
        log.info("connecting to dynamodb signing region {}", endpointConfiguration.getSigningRegion());
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }

    private AmazonDynamoDB build(String region) {
        log.info("connecting to dynamodb region {}", region);
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build();
    }

}
