import math

def compute_expected_negative(x: float) -> float:
    sin_x = math.sin(x)
    cos_x = math.cos(x)
    tan_x = math.tan(x)
    cot_x = cos_x / sin_x
    sec_x = 1.0 / cos_x
    csc_x = 1.0 / sin_x

    numerator = cot_x / sin_x
    numerator += sec_x
    numerator /= cot_x
    numerator /= tan_x
    numerator /= tan_x
    numerator -= cot_x
    numerator -= tan_x
    numerator *= sin_x

    denominator = tan_x * tan_x
    inner = tan_x - (tan_x + cos_x)
    inner -= csc_x
    denominator -= sec_x * inner

    return numerator / denominator


def compute_expected_positive(x: float) -> float:
    log2_x = math.log(x) / math.log(2)
    log3_x = math.log(x) / math.log(3)
    log5_x = math.log(x) / math.log(5)
    ln_x = math.log(x)

    part1 = log2_x + log3_x
    part1 += log5_x
    part1 /= log3_x
    part1 += (ln_x - log2_x)

    part2 = log2_x * (log3_x * log3_x * log3_x)
    part2 *= log3_x

    return part1 * part2


negative_points = [-0.5, -1.0, -2.0]
positive_points = [2.0, 5.0, 10.0]

print("NEGATIVE:")
for x in negative_points:
    value = compute_expected_negative(x)
    print(f"x={x}: {value:.17g}")

print("\nPOSITIVE:")
for x in positive_points:
    value = compute_expected_positive(x)
    print(f"x={x}: {value:.17g}")