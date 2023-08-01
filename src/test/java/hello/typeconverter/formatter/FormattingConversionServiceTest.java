package hello.typeconverter.formatter;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattingConversionServiceTest {
    DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

    @Test
    void formattingConversionService() {
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        conversionService.addFormatter(new MyNumberFormatter());

        // 컨버터 사용
        IpPort ipPort = conversionService.convert("172.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("172.0.0.1", 8080));

        String result = conversionService.convert(new IpPort("172.0.0.1", 8080), String.class);
        assertThat(result).isEqualTo("172.0.0.1:8080");

        // 포매터 사용
        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);

    }
}
