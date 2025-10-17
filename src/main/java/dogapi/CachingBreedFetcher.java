package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private final BreedFetcher fetcher;
    private final HashMap<String, List<String>> breeds = new HashMap<>();

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // return statement included so that the starter code can compile and run.
        if (breeds.containsKey(breed)) { return breeds.get(breed); }
        else {
            callsMade++;
            List<String> subBreeds = new ArrayList<>();

            try {
                subBreeds = fetcher.getSubBreeds(breed);

                if (!subBreeds.isEmpty()) {
                    breeds.put(breed, subBreeds);
                } else {
                    throw new BreedNotFoundException(breed);
                }
            } catch (BreedNotFoundException e) {
                throw new BreedNotFoundException(breed);
            }

            return subBreeds;
        }
    }

    public int getCallsMade() {
        return callsMade;
    }

//    public static void main(String[] args) {
//        DogApiBreedFetcher fetcher = new  DogApiBreedFetcher();
//        CachingBreedFetcher counter = new CachingBreedFetcher(fetcher);
//        System.out.println(counter.getSubBreeds("hound"));
//        counter.getSubBreeds("mastiff");
//        counter.getSubBreeds("terrier");
//        System.out.println(counter.getSubBreeds("terrier"));
//        System.out.println(counter.getCallsMade());
//        System.out.println(counter.breeds);
//    }
}
