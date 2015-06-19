package teamtalk.enums;

/**
 * Created by stokowiec on 2015-06-18.
 */

public enum AudioCodec {

    NoCodec("[0]"),
    Speex("[1,1,4,2,0]"), //[Speex CBR codec id, bandmode, quality, frames per packet, simulate stereo]
    SpeexVBR("[2,1,4,0,0,1,2,0]"), //[Speex VBR codec id, bandmode, VBR quality, bitrate, max bitrate, DTX enabled, frames per packet, simulate stereo]
    OPUS("[3,48000,1,2048,10,1,0,32000,1,0,1920]"); //[OPUS codec id, samplerate, channels, application, complexity, FEC, DTX enabled, bitrate, VBR enabled, VBR constraint, frame size]

    private final String params;

    AudioCodec(String params){
        this.params = params;
    }

    @Override
    public String toString(){
        return params;
    }

}

