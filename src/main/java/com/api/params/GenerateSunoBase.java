package com.api.params;

import lombok.Data;

/**
 * @description:
 * @Author: xJh
 * @Date: 2024/4/7
 */
@Data
public class GenerateSunoBase {
    private String prompt;
    private String mv;
    private String title;
    //是否轻音乐，true/false
    private Boolean makeInstrumental;
    private String tags;
    private String continueAt;
    private String continueClipId;

}
