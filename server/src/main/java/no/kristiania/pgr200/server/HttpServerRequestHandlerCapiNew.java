package no.kristiania.pgr200.server;

import java.io.IOException;

public class HttpServerRequestHandlerCapiNew implements HttpServerRequestHandler {

    String args[];
    String pathRoot, pathTable;
    int pathValue;
    HttpServerRequest request;
    HttpServerResponse response;


    public HttpServerRequestHandlerCapiNew(HttpServerRequest request, HttpServerResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {

        if(!request.getPath().startsWith("capi")){
            return false;
        }

        if(!validateArgs(request.getPath())){
            response.setStatusCode(400);
            response.setBody("Invalid path (number of arguments)");
            return false;
        }

        switch(request.getHttpMethod()){
            case "GET":
                retrieveFromDb();
                break;
            case "PUT":
                updateDb();
                break;
            case "POST":
                insertIntoDb();
                break;
            case "DELETE":
                deleteFromDb();
                break;
            default:
                System.out.println("Invalid HTTP method " + request.getHttpMethod());
                response.setStatusCode(405);
                return false;
        }

    }

    private void retrieveFromDb() {
        if (args.length > 2){

        }
    }

    private boolean validateArgs(String path) {
        args = request.getPath().split("/");
        if (args.length > 1){
            return true;
        }
        return false;
    }
}
