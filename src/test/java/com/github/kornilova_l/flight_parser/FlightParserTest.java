package com.github.kornilova_l.flight_parser;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FlightParserTest {
    @Test
    public void simple() {
        new FlightParser(new File("src/test/resources/recording_java9.jfr"));
    }
}