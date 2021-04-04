package lotto.service;

import lotto.domain.LottoConstant;
import lotto.domain.LottoTicket;
import lotto.domain.LottoTickets;

import java.util.ArrayList;
import java.util.List;

public class LottoPurchaseService {

    private final LottoManualService lottoManualService;
    private final LottoAutoService lottoAutoService;

    public LottoPurchaseService() {
        this.lottoManualService = new LottoManualService();
        this.lottoAutoService = new LottoAutoService();
    }

    public LottoTickets purchaseLottoTickets(int purchaseAmount, List<String> manualLottoPurchaseNumberList) {
        validatePurchasable(purchaseAmount, manualLottoPurchaseNumberList);
        return new LottoTickets(purchaseLottoTicketList(purchaseAmount, manualLottoPurchaseNumberList));
    }

    private void validatePurchasable(int purchaseAmount, List<String> manualLottoPurchaseNumberList) {
        if (purchaseAmount < LottoConstant.LOTTO_PRICE) {
            throw new IllegalArgumentException("최소 1000원 이상의 금액을 입력해 주세요.");
        }
        if (manualLottoPurchaseNumberList == null) {
            throw new IllegalArgumentException("수동로또 구매정보가 입력되지 않았습니다. 입력값을 확인해 주세요.");
        }
        if (purchaseAmount < manualLottoPurchaseNumberList.size() * LottoConstant.LOTTO_PRICE) {
            throw new IllegalArgumentException("구입금액이 부족합니다. 금액과 구매장수를 확인해 주세요.");
        }
    }

    private List<LottoTicket> purchaseLottoTicketList(int purchaseAmount, List<String> manualLottoPurchaseNumberList) {
        List<LottoTicket> lottoTicketList = new ArrayList<>();
        purchaseManualLottoTicket(lottoTicketList, manualLottoPurchaseNumberList);
        purchaseAutoLottoTicket(lottoTicketList, purchaseAmount, manualLottoPurchaseNumberList);
        return lottoTicketList;
    }

    private void purchaseManualLottoTicket(List<LottoTicket> lottoTicketList, List<String> manualLottoPurchaseNumberList) {
        if (manualLottoPurchaseNumberList.size() > 0) {
            lottoTicketList.add(lottoManualService.purchaseLottoTicket(manualLottoPurchaseNumberList));
        }
    }

    private void purchaseAutoLottoTicket(List<LottoTicket> lottoTicketList, int purchaseAmount, List<String> manualLottoPurchaseNumberList) {
        int autoTicketPurchaseCount = calculateAutoTicketPurchaseCount(purchaseAmount, manualLottoPurchaseNumberList);
        if (autoTicketPurchaseCount > 0) {
            lottoTicketList.add(lottoAutoService.purchaseLottoTicket(autoTicketPurchaseCount));
        }
    }

    private int calculateAutoTicketPurchaseCount(int purchaseAmount, List<String> manualLottoPurchaseNumberList) {
        return (purchaseAmount - manualLottoPurchaseNumberList.size() * LottoConstant.LOTTO_PRICE) / LottoConstant.LOTTO_PRICE;
    }
}
