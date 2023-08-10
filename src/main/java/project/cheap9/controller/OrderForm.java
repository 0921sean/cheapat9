package project.cheap9.controller;

import lombok.Getter;
import lombok.Setter;
import project.cheap9.domain.Item;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class OrderForm {

    private int count;

    @NotEmpty(message = "받는 사람 입력은 필수입니다")
    private String name;

    @NotEmpty(message = "연락처 입력은 필수입니다")
    private String number;

    @NotEmpty(message = "우편번호 입력은 필수입니다")
    private String zipcode;

    @NotEmpty(message = "주소 입력은 필수입니다")
    private String address;

    @NotEmpty(message = "동호수 입력은 필수입니다")
    private String dongho;

    @NotEmpty(message = "비밀번호 입력은 필수입니다")
    private String pw;

}
