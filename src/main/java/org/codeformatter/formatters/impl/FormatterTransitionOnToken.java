package org.codeformatter.formatters.impl;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class FormatterTransitionOnToken {

    private String tokenName;

    private String stateToTransferTo;

}
