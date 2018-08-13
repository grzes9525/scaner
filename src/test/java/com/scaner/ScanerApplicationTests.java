package com.scaner;

import com.scaner.generation.GenrateAdvert;
import com.scaner.model.Advert;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScanerApplicationTests {

	@Test
	public void contextLoads() {
		int i=1;

		GenrateAdvert genrateAdvert = new GenrateAdvert();
		for(Advert advert:genrateAdvert.generateAdverts()){
			System.out.println(i+" "+advert.toString());
			System.out.println("---------------------------------------------------------------------------------------");
			i++;
		}

	}



}
