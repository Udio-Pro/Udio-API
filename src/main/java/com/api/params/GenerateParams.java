package com.api.params;

import lombok.Data;

/**
 * @description:
 * @Author: xJh
 * @Date: 2024/4/17
 */
@Data
public class GenerateParams {
    /**
     * commonParams:
     */
    //apiType:Suno 、 Udio
    private String apiType;

    /**
     * Suno :params-------------
     *
     */
    private String prompt;
    /**
     * model: chirp-v3-0
     */
    private String mv;
    private String title;
    /**
     * Instrumental，true/false
     */
    private Boolean makeInstrumental;
    /**
     * eg: classical, folk.....
     */
    private String tags;
    private String continueAt;
    private String continueClipId;


    /**
     * UdioParams：---------------
     * udAudioConditioningType :
     * udAudioConditioningSongId
     * udAudioConditioningPath
     * udPrompt
     * udCustomLyrics
     * */
    // ust type a description of what you’d like to create in the prompt box and click Create.
    // For example, type "a jazz song about New York".
    private String udPrompt;

    private String udCustomLyrics;

    private String udAudioConditioningPath;

    private String udAudioConditioningSongId;

    private String udAudioConditioningType;



    public GenerateSunoBase getSunoBase() {
        GenerateSunoBase sunoBase = new GenerateSunoBase();
        sunoBase.setPrompt(this.prompt);
        sunoBase.setMv(this.mv);
        sunoBase.setTitle(this.title);
        sunoBase.setMakeInstrumental(this.makeInstrumental);
        sunoBase.setTags(this.tags);
        sunoBase.setContinueAt(this.continueAt);
        sunoBase.setContinueClipId(this.continueClipId);
        return sunoBase;
    }
    public GenerateUdioBase getUdioBase(){
        GenerateUdioBase udioBase = new GenerateUdioBase();
        udioBase.setUdPrompt(this.udPrompt);
        udioBase.setUdCustomLyrics(this.udCustomLyrics);
        udioBase.setUdAudioConditioningPath(this.udAudioConditioningPath);
        udioBase.setUdAudioConditioningSongId(this.udAudioConditioningSongId);
        udioBase.setUdAudioConditioningType(this.udAudioConditioningType);
        return udioBase;
    }
}
