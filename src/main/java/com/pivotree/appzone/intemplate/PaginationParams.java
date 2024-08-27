package com.pivotree.appzone.intemplate;

import com.pivotree.appzone.enums.OrderType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PaginationParams {
    @Nonnull
    private String username;
    @Nullable
    private Integer page = 0;
    @Nullable
    private Integer size = 4;
    @Enumerated
    private OrderType type;
}
