package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();
    private static final String API_URL = "https://dog.ceo/api";

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) throws RuntimeException {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        List<String> subBreeds = new ArrayList<>();

        final Request request = new Request.Builder()
                .url(String.format("%s/breed/%s/list", API_URL, breed))
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if  (response.isSuccessful()) {
                final JSONArray subBreedsJSON = (JSONArray) responseBody.get("message");

                for  (int i = 0; i < subBreedsJSON.length(); i++) {
                    subBreeds.add((String) subBreedsJSON.get(i));
                }
            }
            else {
                throw new BreedNotFoundException("Dog breed not found");
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // return statement included so that the starter code can compile and run.
        return subBreeds;
    }

//    public static void main(String[] args) {
//        DogApiBreedFetcher breedFetcher = new DogApiBreedFetcher();
//        System.out.println(breedFetcher.getSubBreeds("Hound"));
//    }
}

