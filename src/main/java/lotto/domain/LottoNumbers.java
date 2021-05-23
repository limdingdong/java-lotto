package lotto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class LottoNumbers implements Iterable<LottoNumber> {

    private static final int MAX_LOTTO_COUNT = 6;

    private final List<LottoNumber> lottoNumbers;

    public LottoNumbers(List<LottoNumber> lottoNumbers) {
        validateLottoNumbers(lottoNumbers);
        this.lottoNumbers = lottoNumbers;
    }

    public static LottoNumbers generate() {
        List<LottoNumber> lottoNumbers = new ArrayList<>();
        for (int i = LottoNumber.MIN_LOTTO_NUMBER; i <= LottoNumber.MAX_LOTTO_NUMBER; i++) {
            lottoNumbers.add(LottoNumber.of(i));
        }
        Collections.shuffle(lottoNumbers);
        return new LottoNumbers(lottoNumbers.stream()
                .limit(MAX_LOTTO_COUNT)
                .collect(Collectors.toList()));
    }

    private void validateLottoNumbers(List<LottoNumber> lottoNumbers) {
        if (lottoNumbers == null) {
            throw new IllegalArgumentException("로또번호를 입력해 주세요.");
        }
        if (lottoNumbers.size() != MAX_LOTTO_COUNT) {
            throw new IllegalArgumentException("로또번호는 " + MAX_LOTTO_COUNT + " 개의 숫자로 이루어져야 합니다.");
        }
        int distinctCount = (int) lottoNumbers.stream().distinct().count();
        if (distinctCount < MAX_LOTTO_COUNT) {
            throw new IllegalArgumentException("중복된 번호가 존재합니다.");
        }
    }

    public int matchCount(LottoNumbers matchTarget) {
        int matchCount = 0;
        for (LottoNumber targetNumber : matchTarget) {
            matchCount += (int) lottoNumbers.stream().filter(lottoNumber -> lottoNumber.equals(targetNumber)).count();
        }
        return matchCount;
    }

    @Override
    public Iterator<LottoNumber> iterator() {
        return lottoNumbers.iterator();
    }
}
