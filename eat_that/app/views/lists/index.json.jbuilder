json.array!(@lists) do |list|
  json.extract! list, :id, :id_recipe, :id_ingredient, :quantity, :unit
  json.url list_url(list, format: :json)
end
