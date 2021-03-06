package poly.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.dto.MelonDTO;
import poly.dto.MelonRankDTO;
import poly.dto.MelonSingerDTO;
import poly.dto.MelonSongDTO;
import poly.persistance.mongo.IMelonMapper;
import poly.service.IMelonService;
import poly.util.CmmUtil;
import poly.util.DateUtil;

@Service("MelonService")
public class MelonService implements IMelonService {

	@Resource(name = "MelonMapper")
	private IMelonMapper melonMapper;

	private Logger log = Logger.getLogger(this.getClass());

	@Override
    public void collectMelonRank() throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".collectMelonRank Start!");

        List<MelonDTO> pList = new ArrayList<>();

        // 멜론 Top100 중 50위까지 정보 가져오는 페이지
        String url = "https://www.melon.com/chart/day/index.htm";

        // JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML소스 저장할 변수
        Document doc;

        doc = Jsoup.connect(url).get();

        // <div class="service_list_song"> 이 태그 내에서 있는 HTML소스만 element에 저장됨
        Elements element = doc.select("div.service_list_song");

        for (Element songInfo : element.select("tr.lst50")) {

            // 크롤링을 통해 데이터 저장하기
            String rank = songInfo.select("span.rank").text(); // 순위
            String song = songInfo.select("div.ellipsis a").eq(0).text(); // 노래
            String singer = songInfo.select("div.ellipsis a").eq(1).text(); // 가수
            String album = songInfo.select("div.ellipsis a").eq(3).text(); // 엘범

            songInfo = null;

            // MongoDB에 저장할 List 형태의 맞는 DTO 데이터 저장하기
            MelonDTO pDTO = new MelonDTO();
            pDTO.setCollect_time(DateUtil.getDateTime("yyyyMMddhhmmss"));
            pDTO.setRank(rank);
            pDTO.setSong(song);
            pDTO.setSinger(singer);
            pDTO.setAlbum(album);

            // 한번에 여러개의 데이터를 MongoDB에 저장할 List 형태의 데이터 저장하기
            pList.add(pDTO);

        }
        
        String colNm = "MelonTOP100_" + DateUtil.getDateTime("yyyyMMdd"); // 생성할 컬렉션명

        // MongoDB Collection 생성하기
        melonMapper.createCollection(colNm);

        // MongoDB에 데이터저장하기
        melonMapper.insertRank(pList, colNm);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".collectMelonRank End!");

    }

	@Override
    public List<MelonDTO> getRank() throws Exception {

        log.info(this.getClass().getName() + ".getRank Start!");

        // 조회할 컬렉션 이름
        String colNm = "MelonTOP100_" + DateUtil.getDateTime("yyyyMMdd");

        List<MelonDTO> rList = melonMapper.getRank(colNm);

        if (rList == null) {
            rList = new ArrayList<>();
        }

        log.info(this.getClass().getName() + ".getRank End!");

        return rList;
    }
	
	@Override
    public List<MelonSongDTO> getSongForSinger() throws Exception {

        log.info(this.getClass().getName() + ".getSongForSinger Start!");

        String colNm = "MelonTOP100_" + DateUtil.getDateTime("yyyyMMdd"); // 조회할 컬렉션명
        String singer = "아이유"; // 조회할 가수

        // 노래별 랭킹 비교결과 가져오기
        List<MelonSongDTO> rList = melonMapper.getSongForSinger(colNm, singer);

        if (rList == null) {
            rList = new ArrayList<>();
        }

        log.info(this.getClass().getName() + ".getSongForSinger End!");

        return rList;
    }
	
	@Override
    public List<MelonSingerDTO> getRankForSinger() throws Exception {

        // 오늘의 랭킹 수집하기
        this.collectMelonRank();

        // 조회할 컬렉션 이름
        String colNm = "MelonTOP100_" + DateUtil.getDateTime("yyyyMMdd");

        // 가수별 랭키 가져오기
        List<MelonSingerDTO> rList = melonMapper.getRankForSinger(colNm);

        if (rList == null) {
            rList = new ArrayList<>();
        }

        return rList;
    }
	
	@Override
    public List<MelonRankDTO> getCompareRank() throws Exception {

        log.info(this.getClass().getName() + ".getCompareRank Start!");

        String curColNm = "MelonTOP100_20210525"; // 현재 랭킹 컬렉션
        String preColNm = "MelonTOP100_20210429"; // 이전 랭킹 컬렉션

        // 노래별 랭킹 비교결과 가져오기
        List<MelonRankDTO> rList = melonMapper.getCompareRank(curColNm, preColNm);

        if (rList == null) {
            rList = new ArrayList<>();
        }

        log.info(this.getClass().getName() + ".getCompareRank End!");

        return rList;
    }
}