class Recipe < ActiveRecord::Base
	has_many :list
	has_many :ingredients, :through => :list

	accepts_nested_attributes_for :ingredients
end
