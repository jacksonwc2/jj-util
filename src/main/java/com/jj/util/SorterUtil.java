package com.jj.util;

import com.jj.util.dto.SortDTO;

public class SorterUtil {

    private SorterUtil() {
        // Garante que a classe não seja instanciada.
    }

    public static SortDTO getSorter(String sort) {
        SortDTO[] sortDTOs = Json.getInstance().fromJson(SortDTO[].class, sort);
        SortDTO sortDTO = null;
        if (sortDTOs != null && sortDTOs.length == IntegerUtil.UM) {
            sortDTO = sortDTOs[0];
        }
        return sortDTO;
    }

}