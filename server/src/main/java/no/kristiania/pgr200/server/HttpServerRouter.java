package no.kristiania.pgr200.server;

import no.kristiania.pgr200.server.controllers.*;

public class HttpServerRouter {

    public AbstractController route(String url){
        if(url.startsWith("capi/conference")){
            return new ConferenceController();
        }

        if(url.startsWith("capi/track")){
            return new TrackController();
        }

        if(url.startsWith("capi/talk")){
            return new TalkController();
        }

        return null;
    }
}
