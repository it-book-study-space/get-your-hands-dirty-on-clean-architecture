package com.example.demo.account.applicaiton.service

import com.example.demo.account.domain.Money

class ThresholdExceededException(
    threshold: Money,
    actual: Money
) : RuntimeException(
    "Maximum threshold for transferring money exceeded: tried to transfer $actual but threshold is $threshold!"
)