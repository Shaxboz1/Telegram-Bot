package org.example;


import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Currents {
    private int id;
    private String Code;
    private String Ccy;
    private String CcyNm_UZ;
    private String CcyNm_EN;
    private String Rate;
    private String Diff;
    private String Date;
}
