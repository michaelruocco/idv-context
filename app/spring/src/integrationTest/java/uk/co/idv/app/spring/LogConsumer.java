package uk.co.idv.app.spring;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.output.OutputFrame;

import java.util.function.Consumer;

@Slf4j
public class LogConsumer implements Consumer<OutputFrame> {

    @Override
    public void accept(OutputFrame frame) {
        log.info(frame.getUtf8String());
    }

}
