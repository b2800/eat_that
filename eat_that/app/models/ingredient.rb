class Ingredient < ActiveRecord::Base
	has_many :list
	has_many :recipes, :through => :list
end
