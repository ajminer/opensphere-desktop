package io.opensphere.infinity.json;

public class Bins
{
    private Terms myTerms;

    private Bucket[] myBuckets;

    public Bins()
    {
    }

    public Bins(String field, int size, long missing)
    {
        myTerms = new Terms(field, size, missing);
    }

    /**
     * Gets the terms.
     *
     * @return the terms
     */
    public Terms getTerms()
    {
        return myTerms;
    }

    /**
     * Sets the terms.
     *
     * @param terms the terms
     */
    public void setTerms(Terms terms)
    {
        myTerms = terms;
    }

    /**
     * Gets the buckets.
     *
     * @return the buckets
     */
    public Bucket[] getBuckets()
    {
        return myBuckets;
    }

    /**
     * Sets the buckets.
     *
     * @param buckets the buckets
     */
    public void setBuckets(Bucket[] buckets)
    {
        myBuckets = buckets;
    }
}