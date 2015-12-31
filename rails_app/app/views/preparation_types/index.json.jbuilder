json.array!(@preparation_types) do |preparation_type|
  json.extract! preparation_type, :id, :name
  json.url preparation_type_url(preparation_type, format: :json)
end
