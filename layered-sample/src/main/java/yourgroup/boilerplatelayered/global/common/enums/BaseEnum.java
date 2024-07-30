package yourgroup.boilerplatelayered.global.common.enums;

// 어떠한 type으로 들어오든 converter에 적용시킬 수 있도록, generic을 <T,K>로 설정
// V는 객체의 value, K는 객체의 Key값을 의미
public interface BaseEnum<V,K> {
    /**
     * 각 Enum은 code와 desc를 return한다
     */
    V getCode();
    K getDescription();
}
