# Context간 Session Clustering Test


## History
이 Test를 하게 된 이유는 /context1 과 /context2간의 context-path가 다를시에 세션 객체를 cluerstering 하기 위함이다.
세션객체를 cluerstering 해주는 이유는 spring security를 쓸경우 session이 cluertering 되어야지 로그인이 유지가 가능하다.
결국 세션클러스터링을 하여서 SSO와 유사한 효과를 내기 위함이다.

## 방법1 - Redis를 사용한 session clusetering
- 이 방법은 Redis가 필요하므로 이 프로젝트에서는 Embeded Redis를 쓰게된다.
- 배포시 사용한다면 redis 의 shutdown을 고려해 이중화 대책이나 전략이 필요 할 것이다.
1. root-context 에서 spring session 및 redis관련 설정이 주석처리 없이 제대로 되어있는지 확인.
2. web.xml에서 spring-session 관련 Filter가 제대로 꽂혀있는지 확인 (필터 순서 중요 springsession->request holder->security 순)
3. 모든 설정이 완료 된후 context1에서 로그인 후 context2에서 로그인 되는지 확인
4. context1/set?name="asd" 후 context2를 들어갔을때 메인에 asd 노출되는지 확인

## 방법2 - Tomcat을 사용한 was 상에서 session cluerstering
- 이 방법은 Redis에 의존성을 탈피하기 위함이다.
- Was에 의존성이 생길 수 있다.
1. tomcat 내부에 context.xml 또는 프로젝트 내부 META-INF 내부에 context.xml에 <Context 에 sessionCookiePath="/" 되어있는지 확인
2. 방법1의 3과 같은방법으로 로그인이 되는지 확인.


### 디버깅사항
1. NetWork 탭에서 request cookie 아이디와 context가 달라졌을때 set-cookie 있는지 보기
2. SecurityContextPersistenceFilter 디버깅 하면서 session attr 확인
3. HttpSessionSecurityContextRepository 디버깅 하면서 session attr 확인