package no.kristiania.pgr200.server;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpServerMethodTests {


    private InputStream createInputStream(String testString) {
        return new ByteArrayInputStream(testString.getBytes());
    }



}
