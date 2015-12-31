json.array!(@ingredients) do |ingredient|
  json.extract! ingredient, :id, :name, :amount_in_stock, :unit
  json.url ingredient_url(ingredient, format: :json)
end
