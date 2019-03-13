package com.n26.entity.request.presentation;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class Transaction {
    /** The amount. */
    private String amount;

    /** The timestamp. */
    @NotNull
    private String timestamp;
}
