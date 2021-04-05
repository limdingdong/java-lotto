package lotto.domain;

import java.util.List;
import java.util.stream.Collectors;

public class LottoTickets {

    private final List<LottoTicket> lottoTicketList;

    public LottoTickets(List<LottoTicket> lottoTicketList) {
        this.lottoTicketList = lottoTicketList;
    }

    public int calculateManualLottoCount() {
        return calculateLottoCount(LottoTicketType.MANUAL);
    }

    public int calculateAutoLottoCount() {
        return calculateLottoCount(LottoTicketType.AUTO);
    }

    private int calculateLottoCount(LottoTicketType lottoTicketType) {
        return lottoTicketList.stream().filter(lottoTicket -> lottoTicket.typeOf(lottoTicketType)).mapToInt(LottoTicket::getCount).sum();
    }

    public List<Lotto> getLottoList() {
        return lottoTicketList.stream().flatMap(lottoTicket -> lottoTicket.getLottoList().stream()).collect(Collectors.toList());
    }
}
