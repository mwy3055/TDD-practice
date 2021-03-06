# Chapter 08. 테스트 가능한 설계

모든 코드를 테스트할 수 있는 건 아니다. 테스트하기 아주 쉬운 코드도 있고, 반대로 테스트하기 거의 불가능한 코드도 있다. 어떻게 해야 테스트하기 쉬운 코드를 작성할 수 있을까? 반대로 어떤 코드가 테스트하기
어려운 코드일까?

## 테스트하기 어려운 코드

### 하드코딩된 변수

DB의 경로, API 주소, 하다못해 사소한 정수 하나까지 거의 모든 하드코딩된 변수가 테스트를 어렵게 만든다. 대부분의 경우 하드코딩된 변수는 테스트 상황에 알맞게 정해지지 않기 때문이다.

예를 들어 실제 앱에서는 API 서버에 요청을 보내야 하지만, 테스트 상황에서는 `FakeAPI`에 요청을 보내기로 했다면? 테스트를 위해 코드를 바꿔야 할 것이다. 그러나 코드를 안전하게 바꾸려면 테스트가 있어야
한다.

또, 테스트가 끝나면 코드를 다시 원래대로 돌려놔야 한다. 깜빡하고 돌려놓지 않았다면? 재앙이 펼쳐질 것이다.

### 의존 객체를 직접 생성

다음의 코드를 보자.

```
public class DataRepository { 
    private val database: Database = SomeDatabase(); 
}
``` 

`DataRepository`가 `Database` 객체를 직접 생성하고 있다.

보통 테스트 코드에서는 `Fake` 데이터베이스를 구현하여 사용는데, `DataRepository`가 데이터베이스 객체를 직접 생성하기 때문에 `FakeDatabase`를 넣기 곤란하다.

### 실행 시점에 따라 달라지는 변수 (메서드)

대표적으로 시간과 관련된 메서드가 있다. 예를 들어 자바의 `LocalDate.now()`는 실행 시간에 따라 다른 값을 반환한다. 이런 함수를 사용하면 어제 성공했던 테스트가 오늘 실패할 수도 있게 되고,
결과적으로 테스트의 신뢰도가 하락한다.

### Single Responsibility Principle을 준수하지 않는 코드

여러 작업을 수행하는 코드 역시 테스트하기 어렵다. 자고로 하나의 메서드는 하나의 일만 해야 한다!

## 테스트하기 쉬운 코드
그렇다면 어떻게 해야 하는가?

* 하드코딩된 상수를 파라미터로 받자.
* 클래스가 의존하는 객체는 직접 생성하지 말고 주입받자
  * 의존성 주입(Dependency Injection)이라고도 한다.
* 복잡한 메서드를 하나의 일만 하는 여러 메서드로 분리하기 
* 외부 라이브러리를 직접 사용하지 말고 감싸서 사용하기
  * 나에게 필요한 기능만 인터페이스로 정의해서 사용!
