package com.wushi.lade.common;

import com.wushi.lade.common.sequence.Sequence;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * @author wushi
 * @date 2020/4/30 10:57
 */
public class SequenceTest {

    private Sequence sequence;

    @Test
    public void Test() {
        sequence = new Sequence(0);
        Long id = sequence.nextId();
        Assert.isTrue(id.compareTo(0L) > 0);
    }
}
