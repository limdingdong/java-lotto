package lotto.controller.dto;

import lotto.domain.LottoNumber;
import lotto.domain.LottoNumbers;
import lotto.domain.LottoTicket;
import lotto.domain.LottoTickets;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LottoNumbersAssembler {

    public static LottoTickets assemblePurchaseTickets(List<LottoNumbersDto> purchaseNumbers) {
        List<LottoTicket> lottoTicketList = purchaseNumbers.stream()
                .map(LottoNumbersAssembler::assembleLottoNumbers)
                .collect(Collectors.toList())
                .stream()
                .map(LottoTicket::new)
                .collect(Collectors.toList());
        return new LottoTickets(lottoTicketList);
    }

    public static LottoNumbers assembleLottoNumbers(LottoNumbersDto numbers) {
        Set<LottoNumber> lottoNumberList = numbers.getLottoNumbers().stream()
                .map(LottoNumber::of)
                .collect(Collectors.toSet());
        return new LottoNumbers(lottoNumberList);
    }
}
