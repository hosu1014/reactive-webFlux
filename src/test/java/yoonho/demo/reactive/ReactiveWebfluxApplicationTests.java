package yoonho.demo.reactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.checkerframework.checker.regex.qual.Regex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.RegexConversion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.dto.Cart.InsertCartReq;
import yoonho.demo.reactive.dto.product.ProductReq;
import yoonho.demo.reactive.dto.test.FruitCnt;
import yoonho.demo.reactive.dto.test.FruitInfo;

@SpringBootTest
@Slf4j
class ReactiveWebfluxApplicationTests {
	
	@Test
	void contextLoads() {
	}
	
	
	void fluxTest() {
		final List<String> basket1 = Arrays
				.asList(new String[] { "kiwi", "orange", "lemon", "orange", "lemon", "kiwi" });
		final List<String> basket2 = Arrays.asList(new String[] { "banana", "lemon", "lemon", "kiwi" });
		final List<String> basket3 = Arrays
				.asList(new String[] { "strawberry", "orange", "lemon", "grape", "strawberry" });
		
		final List<List<String>> baskets = Arrays.asList(basket1, basket2, basket3);
		final Flux<List<String>>  basketFlux = Flux.fromIterable(baskets);
		
		basketFlux.concatMap(basket -> {
		    final Mono<List<String>> distinctFruits = Flux.fromIterable(basket).distinct().collectList();
		    final Mono<Map<String, Long>> countFruitsMono = Flux.fromIterable(basket)
		            .groupBy(fruit -> fruit) // 바구니로 부터 넘어온 과일 기준으로 group을 묶는다.
		            .concatMap(groupedFlux -> groupedFlux.count()
		                .map(count -> {
		                	// log.info("groupecFlux is {}, count is {}", groupedFlux.key(), count);
		                    final Map<String, Long> fruitCount = new LinkedHashMap<>();
		                    fruitCount.put(groupedFlux.key(), count);
		                    return fruitCount;
		                }) // 각 과일별로 개수를 Map으로 리턴
		            ) // concatMap으로 순서보장
		            .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<String, Long>() { {
		                putAll(accumulatedMap);
		                putAll(currentMap);
		            }}); // 그동안 누적된 accumulatedMap에 현재 넘어오는 currentMap을 합쳐서 새로운 Map을 만든다. // map끼리 putAll하여 하나의 Map으로 만든다.
		    
		    
		    return Flux.zip(distinctFruits, countFruitsMono, (distinct, count) -> new FruitInfo(distinct, count));
		}).flatMap(x -> {
			// log.info("fruitInfo is {}", x);
			
			List<String> fruitList = x.getDistinctFruits();
			Map<String, Long> fruitCnt = x.getCountFruits();
			
			
			
			
			List<FruitCnt> fruitCntList = fruitList.stream().map( s -> {
				return new FruitCnt(s, fruitCnt.get(s));
			}).collect(Collectors.toList());

			
			return Flux.fromIterable(fruitCntList);
		}).flatMap(x -> Flux.just(x)).
		subscribe(System.out::println);
		
		
	}
	
	void CartSum() {
		List<InsertCartReq> insertCartReqList = new ArrayList<InsertCartReq>();
		
		InsertCartReq insertCartReq = new InsertCartReq();
		insertCartReq.setSpdNo("PD0001");
		insertCartReq.setSitmNo("PD0001_001");
		insertCartReq.setOdQty(3);
		insertCartReqList.add(insertCartReq);
		
		InsertCartReq insertCartReq1 = new InsertCartReq();
		insertCartReq1.setSpdNo("PD0001");
		insertCartReq1.setSitmNo("PD0001_001");
		insertCartReq1.setOdQty(2);
		insertCartReqList.add(insertCartReq1);

		InsertCartReq insertCartReq2 = new InsertCartReq();
		insertCartReq2.setSpdNo("PD0002");
		insertCartReq2.setSitmNo("PD0002_001");
		insertCartReq2.setOdQty(2);
		insertCartReqList.add(insertCartReq2);
		
		List<InsertCartReq> cartReqList = insertCartReqList.stream()
		.collect(Collectors.groupingBy(cartReq -> cartReq.getSpdNo() + cartReq.getSitmNo() ))
		.entrySet().stream()
		.map(e -> e.getValue().stream()
				.reduce((acc, cur) -> new InsertCartReq(acc.getSpdNo(), acc.getSitmNo(), acc.getOdQty() + cur.getOdQty()))
				)
		.map(op -> op.get())
		.collect(Collectors.toList());
		
		System.out.println(cartReqList);
		
		
	}
	
	@Test 
	void groupedKey() {
		log.info("product is {}",  ProductReq.getProductReqByGroupedKey("LE1206646154$LE1206646154_1236299893$SLE20006"));
	
		String groupedKey = "LE1206646154$LE1206646154_1236299893$SLE20006";
		
		if(StringUtils.isEmpty(groupedKey)) log.info("groupedKey is null");
		String[] groupedKeys = groupedKey.split("\\|");
		Arrays.asList(groupedKeys).stream().forEach(System.out::println);
		
	}
	
	void CartSum1() {
		List<InsertCartReq> insertCartReqList = new ArrayList<InsertCartReq>();
		
		InsertCartReq insertCartReq = new InsertCartReq();
		insertCartReq.setSpdNo("PD0001");
		insertCartReq.setSitmNo("PD0001_001");
		insertCartReq.setOdQty(3);
		insertCartReqList.add(insertCartReq);
		
		InsertCartReq insertCartReq1 = new InsertCartReq();
		insertCartReq1.setSpdNo("PD0001");
		insertCartReq1.setSitmNo("PD0001_001");
		insertCartReq1.setOdQty(2);
		insertCartReqList.add(insertCartReq1);

		InsertCartReq insertCartReq2 = new InsertCartReq();
		insertCartReq2.setSpdNo("PD0002");
		insertCartReq2.setSitmNo("PD0002_001");
		insertCartReq2.setOdQty(2);
		insertCartReqList.add(insertCartReq2);
		
		InsertCartReq insertCartReq3 = new InsertCartReq();
		insertCartReq3.setSpdNo("PD0002");
		insertCartReq3.setSitmNo("PD0002_001");
		insertCartReq3.setOdQty(4);
		insertCartReqList.add(insertCartReq3);
		
		Flux.fromIterable( insertCartReqList)
		.groupBy(cartReq -> cartReq.getSpdNo()+cartReq.getSitmNo())
		.concatMap(g -> {
			log.info("key is {}", g.key());
			
			final Map<String, Flux<InsertCartReq>> cartMap = new LinkedHashMap<>();
			cartMap.put(g.key(), g);             
			return Flux.just(cartMap);
		}).flatMap(map -> {
            log.info("map is {}", map);
            return Flux.fromIterable(map.keySet())
            .map(k -> map.get(k))
            .flatMap(f -> f);
        })
        .subscribe(System.out::println);
//		.reduce((accuMap, curMap) -> new LinkedHashMap<String, Flux<InsertCartReq>>() { {
//			putAll(accuMap);
//			
//			log.info("curmap is {}", curMap);
//			
//            putAll(curMap);
//        }})
//		.map(lmap -> {
//        	log.info("map contents size is {}", lmap.size());
//        	log.info("map contents is {}", lmap);
//			return Flux.fromIterable(lmap.keySet())
//			.flatMap(k -> {
//				Flux<InsertCartReq> mapFlux = lmap.get(k);
//				return mapFlux;
//			});
//		})
//		.flatMapMany(f -> {
//			return f.flatMap(cart -> Flux.just(cart));
//		})
//		.subscribe(System.out::println);
		
	}
	

}
