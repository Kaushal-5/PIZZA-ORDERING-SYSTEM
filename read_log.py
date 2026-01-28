try:
    with open('compile_log.txt', 'rb') as f:
        content = f.read()
    # Decode with replacement to handle any encoding weirdness
    text = content.decode('utf-8', errors='replace')
    print(repr(text))
except Exception as e:
    print(e)
