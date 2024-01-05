package mch.project.hello_world.purposeservice.domain.purpose;

import lombok.Data;

/*
    여러가지 기능이 포함되어 있으므로 main domain에 사용하기 위험하다
    -> 필요한 기능만 별도로 선언해서 사용하는 것이 좋다.
    -> DTO 의 경우는 써도 나쁘지 않다.
 */
@Data
public class Purpose {

    /*
        목표 도메인 모델
        : 목표 ID, 목표 명, 목표 기한, 목표 달성 여부, 상세 내용
    */

    private Long id;
    private String purposeName;
    private String purposeContent;
    private String purposeDue;

    private Boolean open; // 공개 여부

    private Boolean achievement; // 달성 여부
    private PurposeType purposeType; // 목표 타입

    private String categories; // 분류
    private String categoryCode; // 분류 코드

    public Purpose() {}

    public Purpose(String purposeName, String purposeContent, String purposeDue) {
        this.purposeName = purposeName;
        this.purposeContent = purposeContent;
        this.purposeDue = purposeDue;
    }
}
