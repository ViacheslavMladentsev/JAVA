package edu.lieineyes.numbers;

import edu.lieineyes.exception.IllegalNumberException;

public class NumberWorker {

    private static final String EXCEPTION_SIMPLE_NUMBER = "Ошибка: введено число меньше 2";

    public boolean isPrime(int number) throws IllegalNumberException {
        if (number < 2) {
            throw new IllegalNumberException(EXCEPTION_SIMPLE_NUMBER);
        }
        for (int i = 2; i <= number / 2; ++i) {
            if (number % i == 0) {
                return false;
            } else {
                if (i * i >= number) {
                    break;
                }
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        int ret = 0;
        while (number >= 1) {
            ret += number % 10;
            number /= 10;
        }
        return ret;
    }
}
