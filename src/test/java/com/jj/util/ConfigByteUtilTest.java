package com.jj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ConfigByteUtilTest {

    private ConfigByteUtil configByteUtil;
    private byte[] configuracao = { 1, 0, 1, 9, 7, 114, 109, 80, 115, 112, 49, 48, 52, 1, 1, 46, 80, 115, 112, 49, 48, 52, 45, 50, 46, 52, 49, 98, 32,
            45, 32, 67, 97, 100, 97, 115, 116, 114, 111, 32, 100, 101, 32, 80, 97, 114, -30, 109, 101, 116, 114, 111, 115, 32, 100, 111, 115, 32, 80,
            68, 86, 115, 0, 1, 13, 67, 104, 107, 79, 112, 49, 70, 114, 97, 99, 73, 110, 116, 1, 1, 1, 70, 0, 1, 12, 67, 104, 107, 79, 112, 49, 65,
            114, 114, 80, 101, 115, 1, 1, 1, 70, 0, 1, 12, 69, 100, 116, 79, 112, 50, 80, 101, 115, 67, 111, 100, 1, 1, 1, 53, 0, 1, 12, 69, 100, 116,
            79, 112, 50, 80, 101, 115, 80, 114, 99, 1, 1, 1, 53, 0, 1, 12, 69, 100, 116, 79, 112, 51, 77, 115, 103, 77, 111, 110, 1, 1, 40, 81, 85,
            65, 82, 84, 65, 32, 67, 79, 77, 80, 82, 69, 32, 77, 65, 73, 83, 32, 87, 87, 87, 46, 65, 67, 79, 77, 80, 82, 69, 66, 69, 77, 46, 67, 79,
            77, 46, 66, 82, 0, 1, 13, 69, 100, 116, 79, 112, 51, 77, 115, 103, 69, 67, 70, 49, 1, 1, 19, 65, 84, 65, 67, 65, 82, 69, 74, 79, 32, 67,
            79, 77, 80, 82, 69, 66, 69, 77, 0, 1, 13, 69, 100, 116, 79, 112, 51, 77, 115, 103, 69, 67, 70, 50, 1, 1, 30, 65, 81, 85, 73, 32, 86, 79,
            67, 69, 32, 69, 67, 79, 78, 79, 77, 73, 90, 65, 32, 77, 85, 73, 84, 79, 32, 77, 65, 73, 83, 0, 1, 13, 69, 100, 116, 79, 112, 51, 77, 115,
            103, 69, 67, 70, 51, 1, 1, 0, 0, 1, 12, 67, 104, 107, 79, 112, 50, 72, 97, 98, 84, 105, 99, 1, 1, 1, 84, 0, 1, 12, 67, 104, 107, 79, 112,
            50, 72, 97, 98, 67, 97, 114, 1, 1, 1, 70, 0, 1, 12, 67, 104, 107, 79, 112, 50, 73, 109, 112, 67, 117, 112, 1, 1, 1, 70, 0, 1, 12, 77, 109,
            111, 79, 112, 51, 77, 115, 103, 67, 117, 112, 1, 1, 0, 0, 1, 29, 69, 100, 116, 80, 114, 105, 110, 99, 105, 112, 97, 108, 77, 117, 108,
            116, 105, 112, 108, 105, 99, 97, 114, 67, 117, 112, 111, 110, 115, 1, 1, 1, 49, 0, 1, 12, 67, 104, 107, 79, 112, 50, 67, 117, 112, 86,
            108, 114, 1, 1, 1, 70, 0, 1, 12, 69, 100, 116, 79, 112, 50, 67, 117, 112, 86, 108, 114, 1, 1, 4, 48, 44, 48, 48, 0, 1, 12, 67, 104, 107,
            79, 112, 50, 67, 117, 112, 80, 114, 111, 1, 1, 1, 70, 0, 1, 29, 69, 100, 116, 67, 117, 112, 111, 109, 80, 114, 111, 109, 111, 99, 97, 111,
            80, 114, 111, 100, 117, 116, 111, 69, 86, 97, 108, 111, 114, 1, 1, 4, 48, 44, 48, 48, 0, 1, 12, 69, 100, 116, 79, 112, 50, 67, 117, 112,
            80, 114, 111, 1, 1, 1, 48, 0, 1, 12, 67, 104, 107, 79, 112, 49, 82, 99, 98, 77, 111, 101, 1, 1, 1, 70, 0, 1, 12, 67, 104, 107, 79, 112,
            49, 67, 108, 105, 78, 111, 109, 1, 1, 1, 84, 0, 1, 12, 67, 104, 107, 79, 112, 49, 86, 110, 100, 67, 108, 105, 1, 1, 1, 70, 0, 1, 25, 67,
            104, 107, 79, 112, 49, 67, 111, 110, 115, 117, 108, 116, 97, 114, 67, 108, 105, 67, 112, 102, 67, 110, 112, 106, 1, 1, 1, 84, 0, 1, 37,
            67, 104, 107, 83, 111, 108, 105, 99, 105, 116, 97, 114, 69, 110, 100, 101, 114, 101, 99, 111, 69, 110, 116, 114, 101, 103, 97, 73, 100,
            116, 67, 108, 105, 101, 110, 116, 101, 1, 1, 1, 70, 0, 1, 18, 67, 104, 107, 67, 108, 105, 101, 110, 116, 101, 69, 118, 101, 110, 116, 117,
            97, 108, 1, 1, 1, 70, 0, 1, 29, 67, 104, 107, 83, 111, 108, 105, 99, 105, 116, 97, 114, 67, 80, 70, 67, 78, 80, 74, 70, 105, 110, 97, 108,
            86, 101, 110, 100, 97, 1, 1, 1, 84, 0, 1, 25, 67, 104, 107, 71, 101, 114, 97, 114, 67, 65, 84, 53, 50, 65, 112, 111, 115, 82, 101, 100,
            117, 99, 97, 111, 90, 1, 1, 1, 70, 0, 1, 21, 69, 100, 116, 86, 97, 108, 77, 105, 110, 83, 111, 108, 105, 99, 67, 80, 70, 67, 78, 80, 74,
            1, 1, 4, 48, 44, 48, 49, 0, 1, 46, 67, 104, 107, 83, 111, 108, 105, 99, 105, 116, 97, 114, 83, 101, 110, 104, 97, 67, 108, 105, 101, 110,
            116, 101, 65, 112, 101, 110, 97, 115, 65, 111, 70, 105, 110, 97, 108, 105, 122, 97, 114, 86, 101, 110, 100, 97, 1, 1, 1, 70, 0, 1, 12, 67,
            104, 107, 79, 112, 49, 76, 109, 116, 77, 111, 118, 1, 1, 1, 84, 0, 1, 12, 67, 104, 107, 79, 112, 49, 76, 109, 116, 69, 120, 99, 1, 1, 1,
            70, 0, 1, 13, 67, 104, 107, 79, 112, 49, 67, 108, 105, 84, 101, 114, 109, 1, 1, 1, 84, 0, 1, 12, 67, 104, 107, 79, 112, 49, 83, 110, 104,
            86, 110, 100, 1, 1, 1, 70, 0, 1, 12, 67, 104, 107, 79, 112, 49, 73, 109, 112, 68, 97, 100, 1, 1, 1, 70, 0, 1, 12, 67, 104, 107, 79, 112,
            49, 80, 114, 111, 78, 111, 109, 1, 1, 1, 84, 0, 1, 12, 67, 104, 107, 79, 112, 49, 65, 106, 117, 72, 111, 114, 1, 1, 1, 84, 0, 1, 34, 67,
            104, 107, 73, 109, 112, 114, 84, 101, 114, 109, 78, 111, 116, 97, 80, 114, 111, 109, 105, 115, 115, 111, 114, 105, 97, 67, 111, 110, 118,
            101, 110, 105, 111, 1, 1, 1, 70, 0, 1, 36, 99, 104, 107, 73, 109, 112, 114, 105, 109, 105, 114, 68, 97, 100, 111, 115, 67, 82, 77, 82,
            111, 100, 97, 112, 101, 67, 117, 112, 111, 109, 70, 105, 115, 99, 97, 108, 1, 1, 1, 84, 0, 1, 24, 67, 104, 107, 73, 109, 112, 114, 105,
            109, 105, 114, 68, 97, 100, 111, 115, 67, 111, 110, 118, 101, 110, 105, 111, 1, 1, 1, 84, 0, 1, 28, 67, 104, 107, 69, 109, 105, 116, 105,
            114, 67, 69, 83, 84, 101, 78, 67, 77, 67, 117, 112, 111, 109, 70, 105, 115, 99, 97, 108, 1, 1, 1, 70, 0, 1, 46, 67, 104, 107, 73, 109,
            112, 114, 105, 109, 105, 114, 86, 101, 110, 99, 105, 109, 101, 110, 116, 111, 67, 111, 110, 118, 101, 110, 105, 111, 82, 111, 100, 97,
            112, 101, 67, 117, 112, 111, 109, 70, 105, 115, 99, 97, 108, 1, 1, 1, 70, 0, 1, 34, 69, 100, 116, 80, 101, 114, 99, 101, 110, 116, 117,
            97, 108, 84, 111, 108, 101, 114, 97, 110, 99, 105, 97, 73, 116, 101, 109, 80, 101, 115, 97, 118, 101, 108, 1, 1, 0, 0, 1, 25, 67, 104,
            107, 67, 111, 110, 102, 101, 114, 101, 110, 99, 105, 97, 73, 116, 101, 109, 80, 101, 115, 97, 118, 101, 108, 1, 1, 1, 70, 0, 1, 18, 77,
            101, 109, 78, 111, 116, 97, 80, 114, 111, 109, 105, 115, 115, 111, 114, 105, 97, 1, 1, 0, 0, 1, 12, 69, 100, 116, 79, 112, 50, 65, 100,
            99, 73, 110, 105, 1, 1, 1, 53, 0, 1, 12, 69, 100, 116, 79, 112, 50, 65, 100, 99, 84, 97, 109, 1, 1, 2, 49, 48, 0, 1, 6, 69, 100, 116, 84,
            116, 109, 1, 1, 1, 49, 0, 1, 13, 69, 100, 116, 70, 105, 110, 49, 86, 108, 114, 80, 114, 111, 1, 1, 4, 48, 44, 48, 48, 0, 1, 13, 69, 100,
            116, 70, 105, 110, 49, 81, 110, 116, 80, 114, 111, 1, 1, 1, 48, 0, 1, 13, 67, 104, 107, 79, 112, 51, 67, 109, 112, 67, 114, 101, 100, 1,
            1, 1, 70, 0, 1, 14, 69, 100, 116, 79, 112, 51, 86, 105, 97, 115, 67, 114, 101, 100, 1, 1, 1, 50, 0, 1, 12, 67, 104, 107, 79, 112, 50, 86,
            115, 108, 68, 118, 108, 1, 1, 1, 48, 0, 1, 12, 67, 104, 107, 67, 109, 112, 69, 110, 116, 86, 97, 115, 1, 1, 1, 70, 0, 1, 12, 67, 104, 107,
            72, 97, 98, 82, 101, 116, 86, 97, 115, 1, 1, 1, 70, 0, 1, 13, 69, 100, 116, 79, 112, 50, 86, 105, 97, 115, 86, 97, 115, 1, 1, 1, 70, 0, 1,
            17, 71, 114, 98, 83, 97, 105, 100, 97, 86, 97, 115, 105, 108, 104, 97, 109, 101, 1, 1, 1, 84, 0, 1, 20, 69, 100, 116, 67, 111, 101, 83,
            97, 105, 100, 97, 86, 97, 115, 105, 108, 104, 97, 109, 101, 1, 1, 0, 0, 1, 23, 69, 100, 116, 79, 114, 105, 103, 101, 109, 83, 97, 105,
            100, 97, 86, 97, 115, 105, 108, 104, 97, 109, 101, 1, 1, 0, 0, 1, 19, 71, 114, 98, 69, 110, 116, 114, 97, 100, 97, 86, 97, 115, 105, 108,
            104, 97, 109, 101, 1, 1, 1, 84, 0, 1, 22, 69, 100, 116, 67, 111, 101, 69, 110, 116, 114, 97, 100, 97, 86, 97, 115, 105, 108, 104, 97, 109,
            101, 1, 1, 0, 0, 1, 25, 69, 100, 116, 79, 114, 105, 103, 101, 109, 69, 110, 116, 114, 97, 100, 97, 86, 97, 115, 105, 108, 104, 97, 109,
            101, 1, 1, 0, 0, 1, 6, 67, 104, 107, 84, 84, 77, 1, 1, 1, 70, 0, 1, 13, 67, 104, 107, 70, 105, 110, 49, 86, 108, 114, 80, 114, 111, 1, 1,
            1, 70, 0, 1, 13, 67, 104, 107, 70, 105, 110, 49, 81, 110, 116, 80, 114, 111, 1, 1, 1, 70, 0, 1, 9, 67, 104, 107, 79, 112, 50, 86, 115,
            108, 1, 1, 1, 70, 0, 1, 9, 67, 104, 107, 79, 112, 50, 65, 100, 97, 1, 1, 1, 84, 0, 1, 9, 69, 100, 116, 80, 114, 111, 71, 97, 115, 1, 1, 5,
            49, 55, 57, 48, 50, 0, 1, 9, 67, 104, 107, 71, 97, 115, 83, 101, 103, 1, 1, 1, 70, 0, 1, 12, 69, 100, 116, 71, 97, 115, 77, 115, 103, 80,
            114, 99, 1, 2, 9, 122, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 91, 83,
            -76, 68, 76, 65, 83, 13, 10, 65, 74, 68, 65, 83, 74, 68, -57, 65, 74, 83, 68, 79, -57, 65, 74, 75, 68, 81, 87, 79, 69, 68, 76, 13, 10, 13,
            10, 49, 50, 51, 49, 50, 13, 10, 51, 49, 50, 51, 49, 50, 51, 49, 50, 13, 10, 51, 49, 13, 10, 50, 51, 13, 10, 49, 50, 51, 49, 50, 51, 13,
            10, 13, 10, 13, 10, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 91,
            83, -76, 68, 76, 65, 83, 13, 10, 65, 74, 68, 65, 83, 74, 68, -57, 65, 74, 83, 68, 79, -57, 65, 74, 75, 68, 81, 87, 79, 69, 68, 76, 13, 10,
            13, 10, 49, 50, 51, 49, 50, 13, 10, 51, 49, 50, 51, 49, 50, 51, 49, 50, 13, 10, 51, 49, 13, 10, 50, 51, 13, 10, 49, 50, 51, 49, 50, 51,
            13, 10, 13, 10, 13, 10, 13, 10, 13, 10, 13, 10, 13, 10, 13, 10, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32,
            32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 68, 76, 44, 65,
            83, -57, 44, 68, 65, 13, 10, 126, 83, 83, 65, 68, 76, 65, -57, 75, 83, 76, 68, -57, 75, 65, 13, 10, 65, 83, 68, 75, 83, 76, 65, -57, 68,
            75, 65, 68, 75, 65, 13, 10, 91, 76, 65, 83, 75, 68, 76, -57, 65, 83, 75, 68, -61, 44, 83, 68, 65, 13, 10, 65, -57, 83, 68, 75, 65, -57,
            68, 76, 75, 126, -57, 65, 83, 76, 68, 65, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 93, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 68, 65, 13, 10, 68, 65, 65, 13, 10, 68, 83, 65, 13, 10, 65,
            83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 91, 83, -76, 68, 76, 65, 83, 68, 76,
            44, 65, 83, -57, 44, 68, 65, 13, 10, 126, 83, 83, 65, 68, 76, 65, -57, 75, 83, 76, 68, -57, 75, 65, 13, 10, 65, 83, 68, 75, 83, 76, 65,
            -57, 68, 75, 65, 68, 75, 65, 13, 10, 91, 76, 65, 83, 75, 68, 76, -57, 65, 83, 75, 68, -61, 44, 83, 68, 65, 13, 10, 65, -57, 83, 68, 75,
            65, -57, 68, 76, 75, 126, -57, 65, 83, 76, 68, 65, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 93, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 68, 65, 13, 10, 68, 65, 65, 13, 10, 68, 83, 65, 13,
            10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 91, 83, -76, 68, 76, 65, 83,
            68, 76, 44, 65, 83, -57, 44, 68, 65, 13, 10, 126, 83, 83, 65, 68, 76, 65, -57, 75, 83, 76, 68, -57, 75, 65, 13, 10, 65, 83, 68, 75, 83,
            76, 65, -57, 68, 75, 65, 68, 75, 65, 13, 10, 91, 76, 65, 83, 75, 68, 76, -57, 65, 83, 75, 68, -61, 44, 83, 68, 65, 13, 10, 65, -57, 83,
            68, 75, 65, -57, 68, 76, 75, 126, -57, 65, 83, 76, 68, 65, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 93, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 68, 65, 13, 10, 68, 65, 65, 13, 10, 68, 83,
            65, 13, 10, 13, 10, 13, 10, 13, 10, 13, 10, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 13, 10, 65, 83, 68, 68, 68, 68, 68,
            65, 83, 68, 65, 68, 91, 83, -76, 68, 76, 65, 83, 68, 76, 44, 65, 83, -57, 44, 68, 65, 13, 10, 126, 83, 83, 65, 68, 76, 65, -57, 75, 83,
            76, 68, -57, 75, 65, 13, 10, 65, 83, 68, 75, 83, 76, 65, -57, 68, 75, 65, 68, 75, 65, 13, 10, 91, 76, 65, 83, 75, 68, 76, -57, 65, 83, 75,
            68, -61, 44, 83, 68, 65, 13, 10, 65, -57, 83, 68, 75, 65, -57, 68, 76, 75, 126, -57, 65, 83, 76, 68, 65, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 93, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65,
            68, 68, 65, 13, 10, 68, 65, 65, 13, 10, 68, 83, 65, 13, 10, 13, 10, 13, 10, 13, 10, 13, 10, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32,
            32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32,
            32, 32, 32, 68, 76, 44, 65, 83, -57, 44, 68, 65, 13, 10, 126, 83, 83, 65, 68, 76, 65, -57, 75, 83, 76, 68, -57, 75, 65, 13, 10, 65, 83,
            68, 75, 83, 76, 65, -57, 68, 75, 65, 68, 75, 65, 13, 10, 91, 76, 65, 83, 75, 68, 76, -57, 65, 83, 75, 68, -61, 44, 83, 68, 65, 13, 10, 65,
            -57, 83, 68, 75, 65, -57, 68, 76, 75, 126, -57, 65, 83, 76, 68, 65, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 93, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 68, 65, 13, 10, 68, 65, 65, 13,
            10, 68, 83, 65, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 13, 10, 65, 83, 68, 68, 68, 68, 68, 65, 83, 68, 65, 68, 91, 83,
            -76, 68, 76, 65, 83, 68, 76, 44, 65, 83, -57, 44, 68, 65, 13, 10, 126, 83, 83, 65, 68, 76, 65, -57, 75, 83, 76, 68, -57, 75, 65, 13, 10,
            65, 83, 68, 75, 83, 76, 65, -57, 68, 75, 65, 68, 75, 65, 13, 10, 91, 76, 65, 83, 75, 68, 76, -57, 65, 83, 75, 68, -61, 44, 83, 68, 65, 13,
            10, 65, -57, 83, 68, 75, 65, -57, 68, 76, 75, 126, -57, 65, 83, 76, 68, 65, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
            83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83 };

    @Before
    public void setUp() throws Exception {
        configByteUtil = new ConfigByteUtil();

    }

    private String getValor(String chave) {
        return ConfigByteUtil.from(configuracao).adquirirConfiguracao(chave);
    }

    @Test
    public void deveRetornarFParaChkOp1ArrPes() {
        assertTrue(getValor("ChkOp1ArrPes").equals("F"));
    }

    @Test
    public void deveRetornarFParaChkOp1FracInt() {
        assertTrue(getValor("ChkOp1FracInt").equals("F"));
    }

    @Test
    public void deveRetornarTParaChkOp2HabTic() {
        assertTrue(getValor("ChkOp2HabTic").equals("T"));
    }

    @Test
    public void deveRetornarFParaChkOp2CupPro() {
        assertTrue(getValor("ChkOp2CupPro").equals("F"));
    }

    @Test
    public void deveRetornarTParaChkOp1CliTerm() {
        assertTrue(getValor("ChkOp1CliTerm").equals("T"));
    }

    @Test
    public void deveRetornar000ParaEdtOp2CupVlr() {
        assertTrue(getValor("EdtOp2CupVlr").equals("0,00"));
    }

    @Test
    public void deveRetornar0ParaEdtFin1QntPro() {
        assertTrue(getValor("EdtFin1QntPro").equals("0"));
    }

    @Test
    public void deveRetornarTParaGrbSaidaVasilhame() {
        assertTrue(getValor("GrbSaidaVasilhame").equals("T"));
    }

    @Test
    public void deveRetornarVaziaParaCmbEquipamento() {
        assertTrue(getValor("CmbEquipamento").equals(StringUtil.STRING_VAZIA));
    }

    @Test
    public void deveRetornar5ParaEdtOp2PesCod() {
        assertTrue(getValor("EdtOp2PesCod").equals("5"));
    }

    @Test
    public void deveHashMapComParametros() {
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("ChkOp1ArrPes", StringUtil.STRING_VAZIA);
        ret.put("ChkOp2HabTic", StringUtil.STRING_VAZIA);
        ret.put("ChkOp2CupPro", StringUtil.STRING_VAZIA);
        ret.put("ChkOp1CliTerm", StringUtil.STRING_VAZIA);
        ret.put("EdtOp2CupVlr", StringUtil.STRING_VAZIA);
        ret.put("EdtFin1QntPro", StringUtil.STRING_VAZIA);
        ret.put("GrbSaidaVasilhame", StringUtil.STRING_VAZIA);
        ret.put("CmbEquipamento", StringUtil.STRING_VAZIA);
        ret.put("ChkOp1FracInt", StringUtil.STRING_VAZIA);
        ret.put("EdtOp2PesCod", StringUtil.STRING_VAZIA);

        ConfigByteUtil.from(configuracao).adquirirConfiguracao(ret);

        assertTrue(ret.get("ChkOp1ArrPes").equals("F"));
        assertTrue(ret.get("ChkOp2HabTic").equals("T"));
        assertTrue(ret.get("ChkOp2CupPro").equals("F"));
        assertTrue(ret.get("ChkOp1CliTerm").equals("T"));
        assertTrue(ret.get("EdtOp2CupVlr").equals("0,00"));
        assertTrue(ret.get("EdtFin1QntPro").equals("0"));
        assertTrue(ret.get("GrbSaidaVasilhame").equals("T"));
        assertTrue(ret.get("CmbEquipamento").equals(StringUtil.STRING_VAZIA));
        assertTrue(ret.get("ChkOp1FracInt").equals("F"));
        assertTrue(ret.get("EdtOp2PesCod").equals("5"));

    }

    @Test
    public void getNivel() {
        ConfigByteUtil configByteUtil = new ConfigByteUtil();
        configByteUtil.setNivel(IntegerUtil.UM);

        assertEquals(configByteUtil.getNivel(), IntegerUtil.UM);

    }

    @Test
    public void getVersao() {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        lista.add(49);
        lista.add(46);
        lista.add(48);
        lista.add(48);
        lista.add(46);
        lista.add(49);
        lista.add(49);
        lista.add(46);
        lista.add(48);
        lista.add(48);
        lista.add(48);

        int[] versao = { 49, 46, 48, 48, 46, 49, 49, 46, 48, 48, 48 };
        configByteUtil.setVersao(versao);
        assertEquals(Json.getInstance().toJson(configByteUtil.getVersao()), Json.getInstance().toJson(lista));
    }

    @Test
    public void adquirirConfiguracaoPorChaveNivelMenorQueUm() {
        ConfigByteUtil configByteUtil = new ConfigByteUtil();
        configByteUtil.setNivel(IntegerUtil.ZERO);
        assertEquals(configByteUtil.getNivel(), IntegerUtil.ZERO);

    }
}