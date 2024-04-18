package com.api.params;

import lombok.Data;

/**
 * @description:
 * @Author: xJh
 * @Date: 2024/4/17
 */
@Data
public class GenerateUdioBase {
    /**
     * udAudioConditioningType
     * udAudioConditioningSongId
     * udAudioConditioningPath
     * udPrompt
     * udCustomLyrics
     * */
    private String udPrompt;

    //lyricInput, 该参数为""时表示轻音乐，没有传入该参数时或者为null自动生成歌词
    private String udCustomLyrics;
    // The path of the extended song
    private String udAudioConditioningPath;
    //The ID of the extended song
    private String udAudioConditioningSongId;
    //Extend the placement of lyrics without distinction
    //continuation is before ，precede is after
    private String udAudioConditioningType;//continuation 、precede

}
