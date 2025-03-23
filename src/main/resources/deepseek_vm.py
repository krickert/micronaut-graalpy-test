# deepseek_vm.py
from transformers import AutoModelForCausalLM, AutoTokenizer
import torch

model_name = "deepseek-ai/deepseek-coder-1.3b-base"

tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModelForCausalLM.from_pretrained(model_name)

def generate_response(prompt: str):
    inputs = tokenizer(prompt, return_tensors="pt")
    outputs = model.generate(**inputs, max_length=50)
    return tokenizer.decode(outputs[0], skip_special_tokens=True)
