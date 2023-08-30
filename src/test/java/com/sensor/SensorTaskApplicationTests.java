package com.sensor;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SensorTaskApplicationTests {

    Calculator calculator = new Calculator();

    @Test
    void contextLoads() {

        //given
        int num1 = 10;
        int num2 = 20;

        //when
        int result = calculator.add(num1, num2);

        //then
        int expected = 30;
        assertThat(result).isEqualTo(expected);
    }

    class Calculator {
        int add(int num1, int num2) {
            return num1 + num2;
        }
    }


}
