package org.codeformatter.formatters.impl.external_representations;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class FormatterCommandOnToken {

    String tokenName;

    String commandName;

}
