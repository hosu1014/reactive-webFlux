package yoonho.demo.reactive.repository.impl;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import yoonho.demo.reactive.repository.MemberAuthCheckRepository;

@RequiredArgsConstructor
@Slf4j
public class MemberAuthCheckRepositoryImpl implements MemberAuthCheckRepository {
	private final R2dbcEntityTemplate template;
	
	@Override
	public Mono<Long> checkAuthority(String userId, String uri) {
		
		log.info("userId is {}, uri is {}", userId, uri);
		
		DatabaseClient dbClient = template.getDatabaseClient();
		
		return dbClient.sql("select count(r.rt_grp_no) count \r\n"
				+ "   from me_member m\r\n"
				+ "      , st_rt_info r \r\n"
				+ "      , st_rt_tgt_base tb \r\n"
				+ "  where m.rt_grp_no = r.rt_grp_no \r\n"
				+ "    and r.rt_tgt_seq = tb.rt_tgt_seq\r\n"
				+ "    and m.user_id = :userId\r\n"
				+ "    and r.use_yn = 'Y'\r\n"
				+ "    and tb.call_url = :callUrl")
		.bind("userId", userId)
		.bind("callUrl", uri)
		.fetch()
		.one()
		.flatMap(map -> Mono.just(Long.valueOf( map.get("count").toString())));
		
		
	}

}
