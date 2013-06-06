package org.archive.modules.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.io.IOUtils;
import org.archive.modules.CrawlURI;
import org.archive.net.UURI;
import org.archive.net.UURIFactory;
import org.archive.util.Recorder;


public class ExtractorYoutubeFormatStreamTest extends ContentExtractorTestBase {

	protected static final String TEST_URI = "http://www.youtube.com/watch?v=_BFJN62hZp0";
    protected static final String[] EXPECTED_OUTLINKS_ALL = {
    	"http://r3---sn-a5m7znek.c.youtube.com/generate_204",
	    "http://r3---sn-a5m7znek.c.youtube.com/videoplayback?ip=208.70.31.237&key=yt1&newshard=yes&cp=U0hWRVRUUV9MSkNONl9MTlVDOjRtUl9JQzM2NENr&itag=44&mt=1370471490&source=youtube&mv=m&sver=3&ratebypass=yes&ms=au&sparams=cp%2Cid%2Cip%2Cipbits%2Citag%2Cratebypass%2Csource%2Cupn%2Cexpire&ipbits=8&expire=1370493270&fexp=900352%2C924605%2C928201%2C901208%2C929123%2C929121%2C929915%2C929906%2C925714%2C929919%2C929119%2C931202%2C928017%2C912512%2C912518%2C906906%2C904830%2C930807%2C919373%2C906836%2C933701%2C900816%2C912711%2C929606%2C910075&id=fc114937ada1669d&upn=t-LMF5MC9BA&signature=98D0AC4D1B545DE6D5C531A5DB7902877511632A.5700D588C7659DE8A8621EC5110CB638D0EF4A39",
	    "http://r3---sn-a5m7znek.c.youtube.com/videoplayback?ip=208.70.31.237&key=yt1&factor=1.25&newshard=yes&cp=U0hWRVRUUV9MSkNONl9MTlVDOjRtUl9JQzM2NENr&itag=35&sparams=algorithm%2Cburst%2Ccp%2Cfactor%2Cid%2Cip%2Cipbits%2Citag%2Csource%2Cupn%2Cexpire&source=youtube&mv=m&sver=3&fexp=900352%2C924605%2C928201%2C901208%2C929123%2C929121%2C929915%2C929906%2C925714%2C929919%2C929119%2C931202%2C928017%2C912512%2C912518%2C906906%2C904830%2C930807%2C919373%2C906836%2C933701%2C900816%2C912711%2C929606%2C910075&ms=au&algorithm=throttle-factor&id=fc114937ada1669d&expire=1370493270&burst=40&ipbits=8&upn=t-LMF5MC9BA&mt=1370471490&signature=A23283ED964AA8EF061249CCA6199EDDA6543FF2.89798F8181F2250FCFF24F19B2E59D880D054703",
	    "http://r3---sn-a5m7znek.c.youtube.com/videoplayback?ip=208.70.31.237&key=yt1&newshard=yes&cp=U0hWRVRUUV9MSkNONl9MTlVDOjRtUl9JQzM2NENr&itag=43&mt=1370471490&source=youtube&mv=m&sver=3&ratebypass=yes&ms=au&sparams=cp%2Cid%2Cip%2Cipbits%2Citag%2Cratebypass%2Csource%2Cupn%2Cexpire&ipbits=8&expire=1370493270&fexp=900352%2C924605%2C928201%2C901208%2C929123%2C929121%2C929915%2C929906%2C925714%2C929919%2C929119%2C931202%2C928017%2C912512%2C912518%2C906906%2C904830%2C930807%2C919373%2C906836%2C933701%2C900816%2C912711%2C929606%2C910075&id=fc114937ada1669d&upn=t-LMF5MC9BA&signature=2E2A7F8D5A61497159C2A2CFBB07F62B98062500.33826449E77B3A5F5FE40D028857064D7313D60A",
	    "http://r3---sn-a5m7znek.c.youtube.com/videoplayback?ip=208.70.31.237&key=yt1&factor=1.25&newshard=yes&cp=U0hWRVRUUV9MSkNONl9MTlVDOjRtUl9JQzM2NENr&itag=34&sparams=algorithm%2Cburst%2Ccp%2Cfactor%2Cid%2Cip%2Cipbits%2Citag%2Csource%2Cupn%2Cexpire&source=youtube&mv=m&sver=3&fexp=900352%2C924605%2C928201%2C901208%2C929123%2C929121%2C929915%2C929906%2C925714%2C929919%2C929119%2C931202%2C928017%2C912512%2C912518%2C906906%2C904830%2C930807%2C919373%2C906836%2C933701%2C900816%2C912711%2C929606%2C910075&ms=au&algorithm=throttle-factor&id=fc114937ada1669d&expire=1370493270&burst=40&ipbits=8&upn=t-LMF5MC9BA&mt=1370471490&signature=78D14935180C9DA87E1C562719525D0BB6BE21F9.9BC13425338E5DD6C255EA77136CC424830BCD21",
	    "http://r3---sn-a5m7znek.c.youtube.com/videoplayback?ip=208.70.31.237&key=yt1&newshard=yes&cp=U0hWRVRUUV9MSkNONl9MTlVDOjRtUl9JQzM2NENr&itag=18&mt=1370471490&source=youtube&mv=m&sver=3&ratebypass=yes&ms=au&sparams=cp%2Cid%2Cip%2Cipbits%2Citag%2Cratebypass%2Csource%2Cupn%2Cexpire&ipbits=8&expire=1370493270&fexp=900352%2C924605%2C928201%2C901208%2C929123%2C929121%2C929915%2C929906%2C925714%2C929919%2C929119%2C931202%2C928017%2C912512%2C912518%2C906906%2C904830%2C930807%2C919373%2C906836%2C933701%2C900816%2C912711%2C929606%2C910075&id=fc114937ada1669d&upn=t-LMF5MC9BA&signature=9534F06E230AFD98894138B4A5D6DF75D11BC316.A488BE98A3EADBE84BA4A8BDA61E12EB6CA7BB85",
	    "http://r3---sn-a5m7znek.c.youtube.com/videoplayback?ip=208.70.31.237&key=yt1&factor=1.25&newshard=yes&cp=U0hWRVRUUV9MSkNONl9MTlVDOjRtUl9JQzM2NENr&itag=5&sparams=algorithm%2Cburst%2Ccp%2Cfactor%2Cid%2Cip%2Cipbits%2Citag%2Csource%2Cupn%2Cexpire&source=youtube&mv=m&sver=3&fexp=900352%2C924605%2C928201%2C901208%2C929123%2C929121%2C929915%2C929906%2C925714%2C929919%2C929119%2C931202%2C928017%2C912512%2C912518%2C906906%2C904830%2C930807%2C919373%2C906836%2C933701%2C900816%2C912711%2C929606%2C910075&ms=au&algorithm=throttle-factor&id=fc114937ada1669d&expire=1370493270&burst=40&ipbits=8&upn=t-LMF5MC9BA&mt=1370471490&signature=CA92F947487449BAB3D360E565331F5BE50D2134.8EC1CB03A41B459227D84F34D2C2AB7BC2BA95A0",
	    "http://r3---sn-a5m7znek.c.youtube.com/videoplayback?ip=208.70.31.237&key=yt1&factor=1.25&newshard=yes&cp=U0hWRVRUUV9MSkNONl9MTlVDOjRtUl9JQzM2NENr&itag=36&sparams=algorithm%2Cburst%2Ccp%2Cfactor%2Cid%2Cip%2Cipbits%2Citag%2Csource%2Cupn%2Cexpire&source=youtube&mv=m&sver=3&fexp=900352%2C924605%2C928201%2C901208%2C929123%2C929121%2C929915%2C929906%2C925714%2C929919%2C929119%2C931202%2C928017%2C912512%2C912518%2C906906%2C904830%2C930807%2C919373%2C906836%2C933701%2C900816%2C912711%2C929606%2C910075&ms=au&algorithm=throttle-factor&id=fc114937ada1669d&expire=1370493270&burst=40&ipbits=8&upn=t-LMF5MC9BA&mt=1370471490&signature=793FD335B7B7EE9B77B457DA951DAED704FFC363.1B6AC609D71AB3EE17BDA911E2761A87C79A080F",
	    "http://r3---sn-a5m7znek.c.youtube.com/videoplayback?ip=208.70.31.237&key=yt1&factor=1.25&newshard=yes&cp=U0hWRVRUUV9MSkNONl9MTlVDOjRtUl9JQzM2NENr&itag=17&sparams=algorithm%2Cburst%2Ccp%2Cfactor%2Cid%2Cip%2Cipbits%2Citag%2Csource%2Cupn%2Cexpire&source=youtube&mv=m&sver=3&fexp=900352%2C924605%2C928201%2C901208%2C929123%2C929121%2C929915%2C929906%2C925714%2C929919%2C929119%2C931202%2C928017%2C912512%2C912518%2C906906%2C904830%2C930807%2C919373%2C906836%2C933701%2C900816%2C912711%2C929606%2C910075&ms=au&algorithm=throttle-factor&id=fc114937ada1669d&expire=1370493270&burst=40&ipbits=8&upn=t-LMF5MC9BA&mt=1370471490&signature=06294DA91D0E1FE3C7DF7AD9D133EC95939B8D70.731CE14A56E9C61E4ABE43110CE7515C5F42C66B"
    };

    protected static final String[] EXPECTED_OUTLINKS_SUBSET = {
    	"http://r3---sn-a5m7znek.c.youtube.com/generate_204",
	    "http://r3---sn-a5m7znek.c.youtube.com/videoplayback?ip=208.70.31.237&key=yt1&factor=1.25&newshard=yes&cp=U0hWRVRUUV9MSkNONl9MTlVDOjRtUl9JQzM2NENr&itag=34&sparams=algorithm%2Cburst%2Ccp%2Cfactor%2Cid%2Cip%2Cipbits%2Citag%2Csource%2Cupn%2Cexpire&source=youtube&mv=m&sver=3&fexp=900352%2C924605%2C928201%2C901208%2C929123%2C929121%2C929915%2C929906%2C925714%2C929919%2C929119%2C931202%2C928017%2C912512%2C912518%2C906906%2C904830%2C930807%2C919373%2C906836%2C933701%2C900816%2C912711%2C929606%2C910075&ms=au&algorithm=throttle-factor&id=fc114937ada1669d&expire=1370493270&burst=40&ipbits=8&upn=t-LMF5MC9BA&mt=1370471490&signature=78D14935180C9DA87E1C562719525D0BB6BE21F9.9BC13425338E5DD6C255EA77136CC424830BCD21"
    };

    @Override
    protected Extractor makeExtractor() {
        ExtractorYoutubeFormatStream e = new ExtractorYoutubeFormatStream();
        UriErrorLoggerModule ulm = new UnitTestUriLoggerModule();  
        e.setLoggerModule(ulm);
        return e;
    }
    
    public void testYoutubeExtractAll() throws Exception {
        CrawlURI testUri = createTestUri(TEST_URI);
        
        ArrayList<String> itagPriorityList = new ArrayList<String>();
        itagPriorityList.add("44");
        itagPriorityList.add("35");
        itagPriorityList.add("43");
        itagPriorityList.add("34");
        itagPriorityList.add("18");
        itagPriorityList.add("5");
        itagPriorityList.add("36");
        itagPriorityList.add("17");
        ((ExtractorYoutubeFormatStream)extractor).setItagPriority(itagPriorityList);
        ((ExtractorYoutubeFormatStream)extractor).setExtractLimit(10);

        extractor.process(testUri);

		HashSet<Link> expected = new HashSet<Link>();
        for (String expectedLinkString : EXPECTED_OUTLINKS_ALL) {
        	expected.add(new Link(testUri.getUURI(), 
                    UURIFactory.getInstance(expectedLinkString),
                    HTMLLinkContext.EMBED_MISC, Hop.EMBED)
        	);
        }
        assertEquals(expected, testUri.getOutLinks());
    }
    
    public void testDontExtract() throws URIException, UnsupportedEncodingException, IOException, InterruptedException {
    	// not a youtube watch url so shouldProcess() will return false
        CrawlURI testUri = createTestUri("http://archive.org/watch?w=blah");
        extractor.process(testUri);
        assertEquals(Collections.EMPTY_SET, testUri.getOutLinks());
    }

    public void testYoutubeExtractSubset() throws Exception {
        CrawlURI testUri = createTestUri(TEST_URI);
        
        ArrayList<String> itagPriorityList = new ArrayList<String>();
        itagPriorityList.add("37");
        itagPriorityList.add("24");
        itagPriorityList.add("34");
        itagPriorityList.add("35");
        ((ExtractorYoutubeFormatStream)extractor).setItagPriority(itagPriorityList);
        ((ExtractorYoutubeFormatStream)extractor).setExtractLimit(1);

        extractor.process(testUri);

		HashSet<Link> expected = new HashSet<Link>();
        for (String expectedLinkString : EXPECTED_OUTLINKS_SUBSET) {
        	expected.add(new Link(testUri.getUURI(), 
                    UURIFactory.getInstance(expectedLinkString),
                    HTMLLinkContext.EMBED_MISC, Hop.EMBED)
        	);
        }
        assertEquals(expected, testUri.getOutLinks());

    }
	private CrawlURI createTestUri(String urlStr) throws URIException,
			UnsupportedEncodingException, IOException {
		UURI testUuri = UURIFactory.getInstance(urlStr);
        CrawlURI testUri = new CrawlURI(testUuri, null, null, LinkContext.NAVLINK_MISC);

        InputStream is = ExtractorYoutubeFormatStreamTest.class.getClassLoader().getResourceAsStream("ExtractorYoutubeFormatStream.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuilder content = new StringBuilder();
		String line = "";
		while ((line = reader.readLine()) != null) {
		    content.append(line);
		}
        Recorder recorder = createRecorder(content.toString(), "UTF-8");
        IOUtils.closeQuietly(is);

        testUri.setContentType("text/html");
        testUri.setFetchStatus(200);
        testUri.setRecorder(recorder);
        testUri.setContentSize(content.length());
		return testUri;
	}
}
class UnitTestUriLoggerModule implements UriErrorLoggerModule {
    final private static Logger LOGGER = 
        Logger.getLogger(UnitTestUriLoggerModule.class.getName());

    public void info(String info) {
    	LOGGER.log(Level.INFO, info);
    	System.out.println("INFO - "+info);
    }
    public void fine(String info) {
    	System.out.println("Fine - "+info);
    	LOGGER.log(Level.FINE, info);
    	
    }
    public void logUriError(URIException e, UURI u, CharSequence l) {
        LOGGER.log(Level.INFO, u.toString(), e);
        System.out.println("Info - "+ u.toString()+" "+e.toString());
    }

    public Logger getLogger(String name) {
        return LOGGER;
    }

    
    
}